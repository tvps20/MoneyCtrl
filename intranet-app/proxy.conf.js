const PROXY_CONFIG = [
    {
        context: ['/api'],
        target: 'http://localhost:8080',
        secure: false, // true para https
        logLevel: 'debug'
    }
];

module.exports = PROXY_CONFIG;
