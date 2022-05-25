module.exports = {
    devServer: {
        proxy:{
            '/api':{
                target:'http://47.95.192.148',
                secure:false,
                changeOrigin:true,
                pathRewrite:{
                    '^/api':'/api/sys'
                }
            }
        }
    }
}