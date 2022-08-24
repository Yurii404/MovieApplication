'use strict';

angular.module('core.movieSession').factory('MovieSession', ['$http',
    function ($http) {
        return {
            getSessionList: function () {
                return  $http.get('http://localhost:8080/movie-session')
                    .then(function successCallback(response) {
                        return  response.data;

                    }, function errorCallback(response) {
                        console.log(response.statusText);
                    });
            },
            getSessionById: function (sessionId) {
                console.log('http://localhost:8080/movie-session/' + sessionId);
                return  $http.get('http://localhost:8080/movie-session/' + sessionId)
                    .then(function successCallback(response) {
                        return response.data;

                    }, function errorCallback(response) {
                        console.log(response.statusText);
                    });
            }
        };
    }]);

