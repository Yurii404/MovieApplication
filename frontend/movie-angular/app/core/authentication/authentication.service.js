'use strict';

angular.module('core.authentication').factory('Authentication', ['$http',
    function ($http) {
        return {
            register: function (user) {
                return  $http.post('http://localhost:8080/auth/register', user)
                    .then(function successCallback(response) {
                        alert('User ' + user.username + ' was successfully created!');
                        console.log(response.data);

                    }, function errorCallback(response) {
                        alert('User ' + user.username + ' was not successfully created!');
                        console.log(response.statusText);
                    });
            },
            login: function (user) {
                return  $http.post('http://localhost:8080/auth/login', user)
                    .then(function successCallback(response) {
                        alert('You successfully logged!');
                        return response.data;

                    }, function errorCallback(response) {
                        alert('You does not logged!');
                        console.log(response.statusText);
                    });
            }
        };
    }]);

