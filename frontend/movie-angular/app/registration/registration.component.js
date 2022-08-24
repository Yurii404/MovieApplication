'use strict';

// Register `registration` component, along with its associated controller and template
angular.module('registration').component('registration', {
    templateUrl: 'registration/registration.template.html',
    controller: ['Authentication',
        function RegistrationController(Authentication) {
            var self = this;

            self.register = function (user){
                self.user = null;
                Authentication.register(user);
            };


        }
    ]
});
