const PROXY_CONFIG = [
    {
        context: ['/api'],
        target: 'https://moneyctrl-api.herokuapp.com',
        secure: true, // true para https
        logLevel: 'debug'
    }
];

module.exports = PROXY_CONFIG;
