app.controller('challengeController',function($rootScope, $scope, $http, $mdDialog, $mdToast, $filter){
	
	var friendsList;
	
	var chunk = function(arr, size) {
		var newArr = [];
		for (var i=0; i<arr.length; i+=size) {
			newArr.push(arr.slice(i, i+size));
		}
		return newArr;
	};
	
	//Get Friend list
	var friendListURL = $rootScope.fbBaseURL + $rootScope.fbAPIVersion + $rootScope.userId + "/friends?limit=1000" + "&access_token=" +$rootScope.fbToken;
	$rootScope.loading = true;
	$http.get(friendListURL)
	.then(function(response) {
		friendsList = response.data.data;
		var res = chunk(friendsList,4);
		$scope.friends = res;
		$rootScope.loading = false;
    }, function(response) {
		$mdToast.show($mdToast.simple().position('top right').textContent(response.data.responseMessage));
		$rootScope.loading = false;
    });
	
	$scope.challengeConfirm = function(challengedUser) {
		$scope.challengedUser = challengedUser;
		delete $scope.bettingAmount;
		$mdDialog.show({
			templateUrl: '/views/betOverlay.html',
			parent: angular.element(document.body),
			clickOutsideToClose:true,
			scope: $scope,
			preserveScope: true
		});
	};
	
	$scope.challenge = function(){
		
		$rootScope.loading = true;
		$mdDialog.hide();
		var challengeURL = $rootScope.host + "/api/challenge?userId="+$rootScope.userId+"&challengedId="+$scope.challengedUser+"&bettingAmount="+$scope.bettingAmount+"&token="+$rootScope.fbToken;
		$http.post(challengeURL)
		.then(function(response) {
			$rootScope.loading = false;
			$scope.message =  "Hey!,I bet you "+$scope.bettingAmount+ " tokens. Lets find out how much we know each other.";
			$scope.link = $rootScope.host+"/#/bet?betId="+response.data.responseCode;
			$scope.encodeLink = encodeURI($scope.link);
			
			$rootScope.trial = false;
			
			$mdDialog.show({
				templateUrl: '/views/betShare.html',
				parent: angular.element(document.body),
				clickOutsideToClose:true,
				scope: $scope,
				preserveScope: true
			});
			
		}, function(response) {
			$rootScope.loading = false;
			$mdToast.show($mdToast.simple().position('top right').textContent(response.data.responseMessage));
		});
	}
	
	$scope.shareBetFB = function(){
		FB.ui({
			  method: 'share',
			  href: $scope.link,
			  to: $scope.challengedUser,
			  quote: $scope.message,
			  hashtag: "#knowyourfriend"
		}, function(response){});
	};
	
	$scope.notNow = function(){
		$mdDialog.hide();
	};
	
	$scope.searchList = function(){
		$rootScope.loading = true;
		var filtered = $filter('filter')(friendsList,$scope.search);
		var res = chunk(filtered,4);
		$scope.friends = res;
		$rootScope.loading = false;
	};
	
	$scope.reset = function(){
		$rootScope.loading = true;
		$scope.search = undefined;
		var res = chunk(friendsList,4);
		$scope.friends = res;
		$rootScope.loading = false;
	};
	
	$scope.share = function(){
		FB.ui({
			method: 'share',
			href: 'https://knowyourfriend.online'
		}, function(response){});
	};
	
	$scope.openChallenge = function(){
		$scope.challengeConfirm();	
	};
	
});