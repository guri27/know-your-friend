app.controller('loginController',function($rootScope, $scope, $http, $mdDialog, $mdToast, $location){
	
	//Already logged in
	if($rootScope.authenticated){
		$location.path("/profile");
	}
	
	//Login to facebook
	$scope.login = function(){
		$rootScope.loading = true;
		var scope = 'public_profile,user_friends,email';
		FB.login(function(response) {
			if (response.authResponse) {
				FB.api('/me', function(response) {
					var token = FB.getAuthResponse();
					var user = {
						"username":response.name,
						"userId":token.userID,
						"token":token.accessToken
					};
					
					$rootScope.loading = true;
					var loginURL = $rootScope.host+"/api/login";
					$http.post(loginURL,user)
					.then(function(response) {
						$rootScope.loading = false;
						
						$rootScope.authenticated = true;
						$rootScope.fbToken = token.accessToken;
						$rootScope.userId = token.userID;
						$rootScope.username = user.username;
						
						//Check for new/old user
						if(response.data.responseCode == 'SUC_USR_002'){
							$rootScope.isNew = true;
							if($rootScope.betId == undefined){
								$rootScope.trial = true;
								$location.path("/tellUsAbout");
								$mdDialog.show({
								  templateUrl: '/views/overview.html',
								  parent: angular.element(document.body),
								  clickOutsideToClose:true,
								  controller: 'closeController'
								});
							}else {
								$location.path("/bet");
							}
						} else {
							if($rootScope.betId == undefined){
								$location.path("/profile");								
							}else {
								$location.path("/bet");
							}
						}
						
					}, function(response) {
						$rootScope.loading = false;
						$mdToast.show($mdToast.simple().position('top right').textContent(response.data.responseMessage));
					});					
				});
			} else {
				$rootScope.loading = false;
				var alert = $mdDialog.alert().title('Login Failed').textContent('Please click on login again with valid credentials').ok('Close');
				$mdDialog.show(alert);
			}
		},{scope: scope});
	};
	
});

app.controller('closeController',function($scope, $mdDialog){
	//Close
	$scope.close = function(){
		$mdDialog.hide();
	};
});