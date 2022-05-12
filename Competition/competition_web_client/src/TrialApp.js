import React from 'react'
import {GetTrials, AddTrial, DeleteTrial, UpdateTrial} from "./utils/rest-calls";
import TrialTable from './TrialTable'
import TrialForm from './TrialForm'
import TrialFormUpdate from "./TrialFormUpdate";

class TrialApp extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            trials:[],
            deleteFunc:this.deleteFunc.bind(this),
            addFunc:this.addFunc.bind(this),
            updateFunc:this.updateFunc.bind(this),
        }
        console.log("Trial App constructor")
    }

    addFunc(trial){
        console.log('inside add Func '+trial);
        AddTrial(trial)
            .then(rez=>GetTrials())
            .then(trials=>this.setState({trials}))
            .catch(error=>console.log('eroare add',error));
    }

    deleteFunc(trial){
        console.log('inside delete Func '+trial);

        DeleteTrial(trial)
            .then(rez=>GetTrials())
            .then(trials=>this.setState({trials}))
            .catch(error=>console.log("eroare delete",error));
    }

    updateFunc(trial){
        console.log('inside update Func'+trial);
        UpdateTrial(trial,trial.id)
            .then(rez=>GetTrials())
            .then(trials=>this.setState({trials}))
            .catch(error=>console.log("eroare update",error));
    }

   componentDidMount() {
        console.log("inside component did mount");
        GetTrials().then(trials=>this.setState({trials}));
   }

    render() {
        return(
            <div className="TrialApp">
                <h1>Trial Management</h1>
                <TrialForm addFunc={this.state.addFunc}/>
                <br/>
                <br/>
                <h1>Trial Update</h1>
                <TrialFormUpdate updateFunc={this.state.updateFunc}/>
                <br/>
                <br/>
                <TrialTable trials={this.state.trials} deleteFunc={this.state.deleteFunc}/>

            </div>
        );
    }

}

export default TrialApp;