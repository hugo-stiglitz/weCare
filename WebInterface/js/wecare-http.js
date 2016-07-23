'use strict';

angular.module('weCare')

.provider('wcHttp', function () {

    this.$get = function ($http) {
        this.http = $http;
        return this;
    };

    var emergencies = [
        {
            id: 'R1623426',
            message: 'Bewusstlose Frau auf Strasse, nichts näheres bekannt, vermutlich CPR',
            patient: {
                lastname: 'Y',
                firstname: 'X'
            },
            location: {
                city: 'Gaschurn',
                zip: '6800',
                address: 'Unteres Vand 134',
                lng: '12.82563',
                lat: '14.84763'
            },
            status: {
                id: 1,
                name: 'Alarmiert'
            },
            lastStatusUpdate: '12:34:12',
            info: '6 Personen alarmiert'
        },
        {
            id: 'R1623427',
            message: '[atemkreislaufstillstand] ersthelfer vor ort',
            patient: {
                lastname: 'Kastner',
                firstname: 'Kunigunde',
                age: '89'
            },
            location: {
                city: 'Gaschurn',
                zip: '6800',
                address: 'Unteres Vand 134',
                lng: '12.82563',
                lat: '14.84763'
            },
            status: {
                id: 1,
                name: 'Alarmiert'
            },
            lastStatusUpdate: '12:34:12',
            info: '12 Personen alarmiert'
        },
        {
            id: 'R1623428',
            message: 'ACS sympthomatik, Einweiser wartet an der strasse',
            patient: {
                lastname: 'Hartmut',
                firstname: 'Dünser'
            },
            location: {
                city: 'Gaschurn',
                zip: '6800',
                address: 'Unteres Vand 134',
                lng: '12.82563',
                lat: '14.84763'
            },
            status: {
                id: 2,
                name: 'Übernommen'
            },
            lastStatusUpdate: '12:34:12',
            firstResponders: [
                {
                    id: '903748',
                    firstname: 'Martin',
                    lastname: 'Wimmer'
                }
            ],
            info: 'Im Einsatz: Wimmer Martin (903748)'
        },
        {
            id: 'R1623429',
            message: 'CPR, C8 im Anflug',
            patient: {
                lastname: 'Edeltraud',
                firstname: 'Frau'
            },
            location: {
                city: 'Gaschurn',
                zip: '6800',
                address: 'Unteres Vand 134',
                lng: '12.82563',
                lat: '14.84763'
            },
            status: {
                id: 3,
                name: 'Am Einsatzort'
            },
            lastStatusUpdate: '12:34:12',
            firstResponders: [
                {
                    id: '903748',
                    firstname: 'Martin',
                    lastname: 'Wimmer'
                },
                {
                    id: '906694',
                    firstname: 'Lucas',
                    lastname: 'Dobler'
                }
            ],
            info: 'Im Einsatz: Wimmer Martin (903748) und Dobler Lucas (906694)'
        }
    ];

    this.getDispatcher = function (callback) {
        callback({
            id: 1,
            name: 'Stefan Burtscher'
        });
    };

    this.getGeneralInformation = function (callback) {

        var ready = 124;
        var operand = Math.random();
        var difference = Math.round(Math.random() * 3);

        if(operand < 0.2) {
            ready = ready + difference;
        }
        else if(operand > 0.8) {
            ready = ready - difference;
        }

        var result = [
            {
                key: 'First-Responder registriert',
                value: '312'
            },
            {
                key: 'First-Responder in Bereitschaft',
                value: ready
            },
            {
                key: 'First-Responder im Einsatz',
                value: '2'
            },
            {
                key: 'Erfolgreiche Einsätze im Vormonat',
                value: '43'
            },
            {
                key: 'Erfolgreiche Einsätze im Vorjahr',
                value: '137'
            }
        ];

        callback(result);
    };

    this.getActiveEmergencies = function (callback) {

        var tmpStatus = {};
        emergencies.forEach(function(item) {
            var group = JSON.stringify(item.status.id);
            tmpStatus[group] = tmpStatus[group] || [];
            tmpStatus[group].push(item);
        });

        var status = Object.keys(tmpStatus).map(function(group) {
            return {
                id: tmpStatus[group][0].status.id,
                name: tmpStatus[group][0].status.name,
                emergencies: tmpStatus[group]
            };
        });


        callback(emergencies, status);
    };

    this.addNewEmergency = function (emergency, callback) {
        setTimeout(function () {
            emergency.status = {
                id: 0,
                name: 'Erstellt'
            };
            emergency.lastStatusUpdate = '12:34:12';
            emergency.info = 'Suche nach First-Responder';
            emergencies.push(emergency);
            callback();

            setTimeout(function () {
                emergency.status = {
                    id: 1,
                    name: 'Alarmiert'
                };
                emergency.info = '4 Personen alarmiert';
            }, 1000);

            setTimeout(function () {
                emergency.status = {
                    id: 2,
                    name: 'Übernommen'
                };
                emergency.lastStatusUpdate = '12:34:12';
                emergency.firstResponders = [
                    {
                        id: '906694',
                        firstname: 'Lucas',
                        lastname: 'Dobler'
                    }
                ];
                emergency.info = 'Im Einsatz: Dobler Lucas (906694)';
            }, 18000);

            setTimeout(function () {
                emergency.status = {
                    id: 3,
                    name: 'Am Einsatzort'
                };
                emergency.lastStatusUpdate = '12:34:12';
            }, 60000);
        }, 500);
    };
});