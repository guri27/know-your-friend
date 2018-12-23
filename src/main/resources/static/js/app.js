var app = angular.module("KnowYourFriend", ["ngRoute","ngMaterial"]);

//Theme
app.config(function($mdThemingProvider) {
	
	$mdThemingProvider.definePalette('amazingPaletteName', {
	'50': 'rgb(57, 155, 171)',
	'100': 'rgb(57, 155, 171)',
	'200': 'rgb(57, 155, 171)',
	'300': 'rgb(57, 155, 171)',
	'400': 'rgb(57, 155, 171)',
	'500': 'rgb(57, 155, 171)',
	'600': 'rgb(57, 155, 171)',
	'700': 'rgb(57, 155, 171)',
	'800': 'rgb(57, 155, 171)',
	'900': 'rgb(57, 155, 171)',
	'A100': 'rgb(57, 155, 171)',
	'A200': 'rgb(57, 155, 171)',
	'A400': 'rgb(57, 155, 171)',
	'A700': 'rgb(57, 155, 171)',
	'contrastDefaultColor': 'light',    // whether, by default, text (contrast)
										// on this palette should be dark or light
	
	'contrastDarkColors': ['50', '100', //hues which contrast should be 'dark' by default
	'200', '300', '400', 'A100'],
	'contrastLightColors': undefined    // could also specify this if default was 'dark'
	});
	
	$mdThemingProvider.theme('default')
	.primaryPalette('amazingPaletteName');
	
	
	
});

//Route Config
app.config([ "$routeProvider" , function($routeProvider){
	$routeProvider.when("/login",{
		templateUrl : "views/login.html",
		controller : "loginController"
	}).when("/profile",{
		templateUrl : "views/profile.html",
		controller : "profileController"
	}).when("/tellUsAbout",{
		templateUrl : "views/tellUsAbout.html",
		controller : "tellUsAboutController"
	}).when("/challenge",{
		templateUrl : "views/challenge.html",
		controller : "challengeController"	
	}).when("/previous",{
		templateUrl : "views/previous.html",
		controller : "previousController"	
	}).when("/bet",{
		templateUrl : "views/bet.html",
		controller : "betController"
	}).when("/feedback",{
		templateUrl : "views/feedback.html",
		controller : "aboutController"
	})
	.otherwise({
		redirectTo : "/login"
	});
	
}]);


//On location change
app.run(function($rootScope,$location,$routeParams) {
	var user = localStorage.getItem("user");
	if(user != undefined){
		$rootScope.authenticated = true;
		$rootScope.token = user.token;
		$rootScope.userId = user.userId;
	}
	
    $rootScope.$on("$routeChangeStart", function(event, next, current) {

		//Bet redirect
		var betId = $location.search();
		if(betId.betId != undefined){
			$rootScope.betId = betId.betId;
		}
	
		if(!$rootScope.authenticated && next.$$route.originalPath !="/login"){
			$location.path("/login");
		}else {
			if(next.$$route.originalPath == "/tellUsAbout"){
				$rootScope.routeName = "Tell Us About Yourself";
			} else if (next.$$route.originalPath == "/profile"){
				$rootScope.routeName = "Profile";
			}  else if (next.$$route.originalPath == "/challenge"){
				$rootScope.routeName = "Challenge Friends";
			} else if (next.$$route.originalPath == "/bet"){
				$rootScope.routeName = "Bet";
			}  else if (next.$$route.originalPath == "/previous"){
				$rootScope.routeName = "Previous Challenge";
			}
		}
    });
});


app.controller("mainControler",function($rootScope, $scope, $http, $location, $mdSidenav, $mdDialog){	
	
	$rootScope.host = "https://"+$location.host();
	$rootScope.fbBaseURL = "https://graph.facebook.com";
	$rootScope.fbAPIVersion = "/v2.10/";
	
	$scope.openMenu = function(){
		$mdSidenav("left").open();
	}
	
	$scope.closeMenu = function(){
		$mdSidenav("left").close();
	}
	
	$rootScope.close = function(){
		$mdDialog.hide();
	};
});

//Facebook SDK
window.fbAsyncInit = function() {
    FB.init({
      appId            : '1965285233740290',
      autoLogAppEvents : true,
      xfbml            : true,
      version          : 'v2.10'
    });
    FB.AppEvents.logPageView();
};

(function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "https://connect.facebook.net/en_US/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
