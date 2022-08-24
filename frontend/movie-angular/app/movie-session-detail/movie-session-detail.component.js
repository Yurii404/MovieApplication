'use strict';

// Register `phoneList` component, along with its associated controller and template
angular.module('movieSessionDetail').component('movieSessionDetail', {
    templateUrl: 'movie-session-detail/movie-session-detail.template.html',
    controller: ['MovieSession', 'Ticket', '$routeParams',
        function MovieSessionListController(MovieSession, Ticket, $routeParams) {
            var self = this;

            self.sessionId = $routeParams.sessionId;

            var ticket = {sessionId: self.sessionId};

            MovieSession.getSessionById(self.sessionId).then(function (data) {
                    self.movieSession = data;
                }
            );

            self.buyTicket = function () {
                Ticket.buyTicket(ticket).then(function (data) {
                    console.log(data);
                    if(data.status ===201){
                        alert("You successfully bought the ticket");
                    }else {
                        alert("Something went wrong");
                    }
                });

            };


        }
    ]
});
