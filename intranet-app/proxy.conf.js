const PROXY_CONFIG = [
    {
        context: ['/api'],
        target: 'https://moneyctrl-api.herokuapp.com',
        secure: false, // true para https
        logLevel: 'debug'
    }
];

module.exports = PROXY_CONFIG;
