import {TRIAL_BASE_URL} from "./consts";

function status(response){
    console.log('response status '+response.status);
    if (response.status >= 200 && response.status < 300) {
        return Promise.resolve(response)
    } else {
        return Promise.reject(new Error(response.statusText))
    }
}

function json(response) {
    return response.json()
}


export function GetTrials(){
    var headers = new Headers();
    headers.append('Accept','application/json');
    var myInit = {
        method: 'GET',
        headers:headers,
        mode: 'cors'};
    var request = new Request(TRIAL_BASE_URL,myInit);

    console.log('inainte de fetch pentru '+TRIAL_BASE_URL)
    return fetch(request)
        .then(status)
        .then(json)
        .then(data=>{
            console.log("request succeded with JSON response",data);
            return data;
        }).catch(error=>{
            console.log('request failed',error);
            return error;
        })

}

export function AddTrial(trial){
    console.log('inainte de fetch post'+JSON.stringify(trial));

    var myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");
    myHeaders.append("Content-Type","application/json");

    var antet = { method: 'POST',
        headers: myHeaders,
        mode: 'cors',
        body:JSON.stringify(trial)};

    return fetch(TRIAL_BASE_URL,antet)
        .then(status)
        .then(response=>{
            return response.text();
        }).catch(error=>{
            console.log("request failed",error);
            return Promise.reject(error);
        });
}

export function DeleteTrial(id){
    console.log('inainte de fetch delete')
    var myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");

    var antet = { method: 'DELETE',
        headers: myHeaders,
        mode: 'cors'};
    var url = TRIAL_BASE_URL+"/"+id;
    return fetch (url,antet)
        .then(response=>{
            console.log('Delete status '+response.status);
            return response.text();
        }).catch(e=>{
            console.log('error '+e);
            return Promise.reject(e);
        });

}

export function UpdateTrial(trial, id){
    console.log('inainte de fetch update')
    var myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");
    myHeaders.append("Content-Type","application/json");
    var antet = { method: 'PUT',
        headers: myHeaders,
        mode: 'cors',
        body:JSON.stringify(trial)};

    var url = TRIAL_BASE_URL+"/"+id;
    console.log(url);
    console.log(antet);
    return fetch (url,antet)
        .then(response=>{
            console.log('update status '+response.status);
            return response.text();
        }).catch(e=>{
            console.log('error '+e);
            return Promise.reject(e);
        });
}