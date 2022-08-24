'use strict';

angular.module('movieApp').config(['$routeProvider',
    function config($routeProvider) {
        $routeProvider.when('/cinema', {
            template: '<cinema-list></cinema-list>'
        }).when('/cinema/:cinemaId', {
            template: '<movie-room-list></movie-room-list>'
        }).when('/movieRoom/:roomId', {
            template: '<movie-session-list></movie-session-list>'
        }).when('/movieSession/:sessionId', {
            template: '<movie-session-detail></movie-session-detail>'
        }).when('/auth/register', {
            template: '<registration></registration>'
        }).when('/auth/login', {
            template: '<login></login>'
        }).otherwise('/cinema');
    }
]);
