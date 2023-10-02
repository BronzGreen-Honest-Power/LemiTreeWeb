import config from './frontend_config.json' assert { type: 'json' };

// Kotlin must match the name of the function, for example:
// external fun getConfig()
export function getConfig(){
    return config
}

