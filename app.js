const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const config = require.main.require('./config.js');
const request = require('request');

app.use(bodyParser.json({ limit: '5mb', 'parameterLimit': 2000 }));
app.use(bodyParser.urlencoded({ extended: true, limit: '5mb', 'parameterLimit': 2000 }));

app.post('/health-check',(req,res)=>{
    if(!(req.body && req.body.Sleep && req.body.sugarCountBefore && req.body.sugarCountAfter && req.body.totalCaloriesIntake && req.body.totalCaloriesBurnt)){
        return res.json({status:0,msg:'Please send all params',data:{}});
    }
    var data = req.body,retData = {status:0,msg:'err getting data',data:{}},response;
    data =  {
        Inputs:{
            input1:
                    [
                        {
                            Sleep:data.Sleep,
                            sugarCountBefore:data.sugarCountBefore,
                            sugarCountAfter:data.sugarCountAfter,
                            totalCaloriesIntake:data.totalCaloriesIntake,
                            totalCaloriesBurnt:data.totalCaloriesBurnt
                        }
                    ]
                }
            }; 
    var options = {
        method:'POST',
        uri: 'https://ussouthcentral.services.azureml.net/workspaces/36b11924aefc4a4f8933bf1d27f1000f/services/dfe000d4ddc34fc29834040c9772d9bb/execute?api-version=2.0&format=swagger',
        headers: {
            'Content-type': 'application/json',
            'cache-control': 'no-cache',
            'Authorization': 'Bearer /sM1FjxmIimC6YmGw6krNHLGC8Y7XXaTtvQwESGeUYrTcavoMhiSEpYPKvwULGsBUDujpuoQJzUn0Ay85Ms7Ng=='
        },
        body: JSON.stringify(data),
    }
    request(options,(err,response,body)=>{
        if(err){
            console.log('Error from azure api',err);
            return res.json(retData);
        }
        retData.status = response.statusCode;
        retData.msg = 'response received successfully';
        response = JSON.parse(body);
        retData.data = response && response.Results && response.Results.output1 && response.Results.output1.length &&  response.Results.output1[0]["Scored Probabilities"]? response.Results.output1[0]["Scored Probabilities"] : 0;
        return res.json(retData);
    });
});
app.listen(5600,()=>{
 console.log('Express listening at 5600');
});