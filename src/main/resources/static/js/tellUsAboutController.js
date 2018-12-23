app.controller('tellUsAboutController',function($rootScope, $scope, $http, $mdDialog, $mdToast, $location){
	
	var chunk = function(arr, size) {
		var newArr = [];
		for (var i=0; i<arr.length; i+=size) {
			newArr.push(arr.slice(i, i+size));
		}
		return newArr;
	};
	
	var questionURL = $rootScope.host + "/api/question?userId="+$rootScope.userId+"&token="+$rootScope.fbToken;
	$rootScope.loading = true;
	$http.get(questionURL)
    .then(function(response) {
		var res = chunk(response.data,2);
		$scope.questions = res;
		$rootScope.loading = false;
    }, function(response) {
		$rootScope.loading = false;
		$mdToast.show($mdToast.simple().position('top right').textContent(response.data.responseMessage));
    });
	
	/**
		This function will update user responses
	*/
	$scope.saveTellUs = function(){
		$rootScope.loading = true;
		var updateReq = [];
		angular.forEach($scope.questions,function(grid){
			angular.forEach(grid,function(que){
				updateReq.push(que);
			});
		});
		$http.post(questionURL,updateReq)
		.then(function(response) {
			$rootScope.loading = false;
			$mdToast.show($mdToast.simple().position('top right').textContent('Answers updated'));
			$location.path("/challenge");
		}, function(response) {
			$rootScope.loading = false;
			var alert = $mdDialog.alert().title('Tell Us About').textContent(response.data.responseMessage).ok('Close');
			$mdDialog.show(alert);
		});
	};
});