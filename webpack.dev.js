const merge = require('webpack-merge');
const common = require('./webpack.common.js');

module.exports = merge(common, {
    mode: 'development',
    devtool: 'source-map',
    devServer: {
        contentBase: './dist',
        compress: true,
        port: 8001,
        allowedHosts: [
            'localhost:8081'
        ],
        stats: 'errors-only',
        clientLogLevel: 'error',
    },
});