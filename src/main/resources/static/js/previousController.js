app.controller('previousController',function($rootScope, $scope, $http, $mdDialog, $mdToast){
	
	var chunk = function(arr, size) {
		var newArr = [];
		for (var i=0; i<arr.length; i+=size) {
			newArr.push(arr.slice(i, i+size));
		}
		return newArr;
	};
	
	var previousURL = $rootScope.host+"/api/user/challenge?userId="+$rootScope.userId+"&token="+$rootScope.fbToken;
	$rootScope.loading = true;
	$http.get(previousURL)
	.then(function(response) {
		var userChallenge = chunk(response.data.userChallenge,4);
		$scope.userChallenge = userChallenge;
		
		var userChallenged = chunk(response.data.userChallenged,4);
		$scope.userChallenged = userChallenged;
		
		$rootScope.loading = false;
    }, function(response) {
		$rootScope.loading = false;
		$mdToast.show($mdToast.simple().position('top right').textContent(response.data.responseMessage));
    });
	
	
	$scope.viewAns = function(chlId, init){
		var confirm = $mdDialog.confirm()
		.title("View Answer")
		.textContent("View answers by paying 5 tokens ?")
		.ariaLabel('Bet')
		.ok("Yes")
		.cancel("Not Now");

		$mdDialog.show(confirm).then(function() {
			viewAjax(chlId,init);
		}, function() {
			$mdDialog.hide();
		});
	};
	
	function viewAjax(chlId, init){
		var viewURL = $rootScope.host+"/api/challenge/"+chlId+"/paid?init="+init+"&userId="+$rootScope.userId+"&token="+$rootScope.fbToken;
		$rootScope.loading = true;
		$http.get(viewURL)
		.then(function(response) {
			$scope.paidAnswers = response.data;
			viewDialog();
			$rootScope.loading = false;
		}, function(response) {
			$rootScope.loading = false;
			$mdToast.show($mdToast.simple().position('top right').textContent(response.data.responseMessage));
		});
	}
	
	function viewDialog(){
		$mdDialog.show({
			templateUrl: '/views/paidAnswer.html',
			parent: angular.element(document.body),
			clickOutsideToClose:false,
			scope: $scope,
			preserveScope: true,
			fullscreen: true
		});
	};
	
});