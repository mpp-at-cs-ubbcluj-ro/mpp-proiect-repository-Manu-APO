
import React from 'react'

class TrialFormUpdate extends React.Component{
    constructor(props) {
        super(props);
        this.state = {id: '', maxNumberOfParticipants: '', trialType: '', ageCategory: ''};
    }

    handleId = (event) => {
        this.setState({id: event.target.value});
    }

    handleMaxNumberOfParticipants = (event) => {
        this.setState({maxNumberOfParticipants: event.target.value});
    }

    handleTrialType = (event) => {
        this.setState({trialType: event.target.value});
    }

    handleAgeCategory = (event) => {
        this.setState({ageCategory: event.target.value});
    }

    handleSubmit=(event)=>{
        var trial = {
            id: this.state.id,
            maxNumberOfParticipants: this.state.maxNumberOfParticipants,
            trialType: this.state.trialType,
            ageCategory: this.state.ageCategory
        }
        console.log("a test length was submitted: ");
        console.log(trial);
        this.props.updateFunc(trial);
        event.preventDefault();
    }

    render(){
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    id:
                    <input type="text" value={this.state.id} onChange={this.handleId}/>
                </label><br/>
                <label>
                    max Number Of Participants:
                    <input type="text" value={this.state.maxNumberOfParticipants} onChange={this.handleMaxNumberOfParticipants}/>
                </label><br/>
                <label>
                    trial Type:
                    <input type="text" value={this.state.trialType} onChange={this.handleTrialType}/>
                </label><br/>
                <label>
                    age Category:
                    <input type="text" value={this.state.ageCategory} onChange={this.handleAgeCategory}/>
                </label><br/>

                <input type="submit" value="Submit"/>
            </form>
        );
    }


}

export default TrialFormUpdate;