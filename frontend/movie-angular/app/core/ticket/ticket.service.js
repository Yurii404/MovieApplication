'use strict';

angular.module('core.ticket').factory('Ticket', ['$http',
    function ($http) {
        return {
            buyTicket: function (ticket) {

                return $http({
                    method: "POST",
                    url: 'http://localhost:8080/tickets',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + localStorage.getItem('token'),
                        'X-Requested-With': 'XMLHttpRequest'
                    },
                    data: ticket
                }).then(function successCallback(response) {
                    return response;

                }, function errorCallback(response) {
                    return response;
                });



            }
        };
    }]);

