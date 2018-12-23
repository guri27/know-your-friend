app.controller('betController',function($rootScope, $scope, $http, $mdDialog, $mdToast, $location){
		
	// Save bet
	var betId;
	if($rootScope.betId != undefined){
		betId = $rootScope.betId;
		delete $rootScope.betId;
	}
	
	var challengeURL = $rootScope.host + "/api/challenge/"+betId+"?userId="+$rootScope.userId+"&token="+$rootScope.fbToken+"&username="+$rootScope.username;
	
	$rootScope.loading = true;
	// Get bet details
	$http.get(challengeURL)
	.then(function(response) {
		$scope.betDetail = response.data;
		$rootScope.loading = false;
		accept();
    }, function(response) {
		$rootScope.loading = false;
		$mdToast.show($mdToast.simple().position('top right').textContent(response.data.responseMessage));
		$location.path("/profile");
    });
	
	// Accept bet
	function accept(){
		var confirm = $mdDialog.confirm()
			  .title($scope.betDetail.user+" challenged you !")
			  .textContent("Do you want to accept the bet of "+$scope.betDetail.betAmount+ " tokens ?")
			  .ariaLabel('Bet')
			  .ok("Accept")
			  .cancel("Not Now");

		$mdDialog.show(confirm).then(function() {
			acceptAjax();
		}, function() {
			var alert = $mdDialog.alert().title('Accept Bet').textContent('Click on link shared on facebook again to get bet').ok('Close');
			$mdDialog.show(alert);
			$mdDialog.hide();
			$location.path("/profile");
		});
	};
	
	function acceptAjax(){
		$rootScope.loading = true;
		$http.put(challengeURL)
		.then(function(response) {
			$rootScope.loading = false;
			$rootScope.challengedBy = response.data.responseMessage;
			getQuestions();
		}, function(response) {
			$rootScope.loading = false;
			$mdToast.show($mdToast.simple().position('top right').textContent(response.data.responseMessage));
			accept();
		});
	};
	
	var questionURL = $rootScope.host + "/api/challenge/"+betId+"/bet?userId="+$rootScope.userId+"&token="+$rootScope.fbToken+"&username="+$rootScope.username;
	function getQuestions(){
		$http.get(questionURL)
		.then(function(response) {
			var res = chunk(response.data,2);
			$scope.questions = res;
			$rootScope.loading = false;
		}, function(response) {
			$rootScope.loading = false;
			$mdToast.show($mdToast.simple().position('top right').textContent(response.data.responseMessage));
		});
	}
	
	var chunk = function(arr, size) {
		var newArr = [];
		for (var i=0; i<arr.length; i+=size) {
			newArr.push(arr.slice(i, i+size));
		}
		return newArr;
	};
	
		
	/**
	 * This function will update user responses
	 */
	$scope.saveTellUs = function(){
		$rootScope.loading = true;
		var updateReq = [];
		angular.forEach($scope.questions,function(grid){
			angular.forEach(grid,function(que){
				updateReq.push(que);
			});
		});
		$http.post(challengeURL,updateReq)
		.then(function(response) {
			$rootScope.loading = false;
			$rootScope.result = response.data.responseCode;
			$rootScope.resultMsg = response.data.responseMessage;
			
			$mdDialog.show({
				templateUrl: '/views/betResult.html',
				parent: angular.element(document.body),
				clickOutsideToClose:true,
				controller: 'betResultController',
			});
			
			$location.path("/profile");
			
		}, function(response) {
			$rootScope.loading = false;
			$mdToast.show($mdToast.simple().position('top right').textContent(response.data.responseMessage));
		});
	};
	
	$location.search("betId", null);
	
});

app.controller('betResultController',function($rootScope, $scope, $mdDialog){
	
	
	var winArray = ["https://knowyourfriend.online/redirect/win/win1.html","https://knowyourfriend.online/redirect/win/win2.html","https://knowyourfriend.online/redirect/win/win3.html","https://knowyourfriend.online/redirect/win/win4.html"];
	var lossArray = ["https://knowyourfriend.online/redirect/loss/loss1.html","https://knowyourfriend.online/redirect/loss/loss2.html","https://knowyourfriend.online/redirect/loss/loss3.html","https://knowyourfriend.online/redirect/loss/loss4.html"];
	
	var result = $rootScope.result;
	$scope.resultMsg = $rootScope.resultMsg;
	var challengedBy = $rootScope.challengedBy;
	
	$scope.shareResult = function(){
		var image;
		if(result == "WIN"){
			image = winArray[Math.floor(Math.random() * winArray.length)];
		}else if (result == "LOSS"){
			image = lossArray[Math.floor(Math.random() * lossArray.length)];
		}
		
		console.log("Image: " +image+ " & Id: "+challengedBy);
		
		FB.ui({
			  method: 'share',
			  href: image,
			  to: challengedBy,
			  hashtag: "#knowyourfriend"
		}, function(response){});
	};
	
	$scope.notNow = function(){
		$mdDialog.hide();
	};
	
});