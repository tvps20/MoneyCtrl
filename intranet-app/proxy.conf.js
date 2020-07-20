const PROXY_CONFIG = [
    {
        context: ['/api'],
        target: 'http://localhost:8080', //'https://moneyctrl-api.herokuapp.com',
        secure: true, // true para https
        changeOrigin: true,
        logLevel: 'debug'
    }
];

module.exports = PROXY_CONFIG;
