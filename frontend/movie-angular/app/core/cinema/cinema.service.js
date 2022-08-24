'use strict';

angular.module('core.cinema').factory('Cinema', ['$http',
    function ($http) {
        return {
            getData: function () {
                return  $http.get('http://localhost:8080/cinema')
                    .then(function successCallback(response) {
                        return  response.data;

                    }, function errorCallback(response) {
                        console.log(response.statusText);
                    });
            }
        };
    }]);

