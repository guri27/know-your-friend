app.controller('profileController',function($rootScope, $scope, $http, $mdDialog, $mdToast, $window, $route){
	
	$scope.profilePicSRC = $rootScope.fbBaseURL + $rootScope.fbAPIVersion + $rootScope.userId + "/picture?width=200&height=200&access_token=" +$rootScope.fbToken;
	
	var profileURL = $rootScope.host + "/api/user?userId="+$rootScope.userId+"&token="+$rootScope.fbToken;
	$rootScope.loading = true;
	$http.get(profileURL)
    .then(function(response) {
		$rootScope.loading = false;
		$scope.profile = response.data;
		$rootScope.trial = $scope.profile.trial;
    }, function(response) {
		$rootScope.loading = false;
		$mdToast.show($mdToast.simple().position('top right').textContent(response.data.responseMessage));
    });
	
	$scope.addToken = function(){
		$mdDialog.hide();	
		$rootScope.loading = true;
		var addTokenURL = $rootScope.host + "/api/user/"+$scope.tokenAmount+"?userId="+$rootScope.userId+"&token="+$rootScope.fbToken;
		$http.post(addTokenURL)
		.then(function(response) {
			$rootScope.loading = false;
			$scope.urlCreated = true;
			$window.open(response.data.paymentURL.slice(0,-11), "_self");
		}, function(response) {
			$rootScope.loading = false;
			$mdToast.show($mdToast.simple().position('top right').textContent(response.data.responseMessage));
		});		
	};
	
	$scope.tokenDialog = function(){
		$mdDialog.show({
			templateUrl: '/views/tokenOverlay.html',
			parent: angular.element(document.body),
			clickOutsideToClose:true,
			scope: $scope,
			preserveScope: true
		});		
	};
	
	$scope.notNow = function(){
		$mdDialog.hide();
	};
	
	$scope.redeemToken = function(){
		$mdDialog.hide();
		if($scope.profile.available <=199){
			$mdToast.show($mdToast.simple().position('top right').textContent("Availble tokens are less than 200"));
		}else{
			$rootScope.loading = true;
			var redeemURL = $rootScope.host + "/api/redeem/"+$scope.email+"?userId="+$rootScope.userId+"&token="+$rootScope.fbToken;
			$http.post(redeemURL)
				.then(function(response) {
					$rootScope.loading = false;
					$route.reload();
					var alert = $mdDialog.alert().title('Redeem').textContent("Amazon voucher will be send on email provided. Please give us few hours to process.").ok('Close');
					$mdDialog.show(alert);
				}, function(response) {
					$rootScope.loading = false;
					$mdToast.show($mdToast.simple().position('top right').textContent(response.data.responseMessage));
			});
		}
	};
	
	$scope.redeemDialog = function(){
		$mdDialog.show({
			templateUrl: '/views/redeemOverlay.html',
			parent: angular.element(document.body),
			clickOutsideToClose:true,
			scope: $scope,
			preserveScope: true
		});
	};
	
});