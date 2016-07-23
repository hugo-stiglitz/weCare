'use strict';

angular.module('weCare')

    .controller('MainCtrl', ['$scope', '$mdDialog', '$mdSidenav', '$interval', 'wcHttp', function ($scope, $mdDialog, $mdSidenav, $interval, http) {

        http.getDispatcher(function (dispatcher) {
            $scope.dispatcher = dispatcher;
        });

        var updateData = function () {
            http.getGeneralInformation(function (info) {
                $scope.generalInfo = info;
            });
            http.getActiveEmergencies(function (emergencies, status) {
                $scope.activeEmergencies = emergencies;
                $scope.activeStatus = status;
            });
        };

        updateData();
        $interval(function () {
            updateData();
        }, 2000);

        $scope.toggleMenu = function () {
            $mdSidenav('menu').toggle();
        }

        var originatorEv;
        $scope.openMore = function ($mdOpenMenu, ev) {
            originatorEv = ev;
            $mdOpenMenu(ev);
        };

        $scope.showAuthors = function (ev) {
            $mdDialog.show(
                $mdDialog.alert()
                    .targetEvent(ev)
                    .clickOutsideToClose(true)
                    .parent('body')
                    .title('Author')
                    .textContent('Lucas Dobler')
                    .ok('OK')
            );
            originatorEv = null;
        };

        $scope.newEmergency = function (ev) {
            $mdDialog.show({
                controller: function ($scope) {
                    $scope.emergency = {
                        id: 'R1623430'
                    };

                    $scope.isAlertable = function () {
                        return (
                                $scope.emergency.cpr ||
                                $scope.emergency.unconsciousness ||
                                $scope.emergency.unknown
                            )
                            &&
                            (
                                !$scope.emergency.violence
                            );
                    };

                    var loading = false;
                    $scope.isLoading = function () {
                        return loading;
                    }

                    $scope.alert = function() {
                        loading = true;
                        http.addNewEmergency($scope.emergency, function () {
                            // TODO show message
                            updateData();
                            loading = false;
                            $mdDialog.hide();
                        });
                    };

                    $scope.close = function () {
                        $mdDialog.cancel();
                    };
                },
                targetEvent: ev,
                templateUrl: 'dialog/newEmergency.tmpl.html',
                parent: angular.element(document.body),
                clickOutsideToClose: false
            });
        };

        $scope.import = function (ev) {
            $mdDialog.show({
                controller: function ($scope) {
                    $scope.load = function () {
                        graph.import($scope.data);
                        $mdDialog.hide();
                    };
                    $scope.close = function () {
                        $mdDialog.cancel();
                    };
                },
                templateUrl: 'dialog/import.tmpl.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose:true,
                fullscreen: false
            });
        };

        $scope.export = function (ev) {
            $mdDialog.show({
                controller: function ($scope) {
                    $scope.data = graph.export();
                    $scope.close = function () {
                        $mdDialog.hide();
                    };
                    $scope.close = function () {
                        $mdDialog.cancel();
                    };
                },
                templateUrl: 'dialog/export.tmpl.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose:true,
                fullscreen: false
            });
        };
    }]);