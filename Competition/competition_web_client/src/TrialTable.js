import React from 'react';

class TrialRow extends React.Component{
    handleClick=(event)=>{
        console.log("delete button pentru "+this.props.trial.id);
        this.props.deleteFunc(this.props.trial.id);
    }
    render() {
        return(
            <tr>
                <td>{this.props.trial.id}</td>
                <td>{this.props.trial.maxNumberOfParticipants}</td>
                <td>{this.props.trial.trialType}</td>
                <td>{this.props.trial.ageCategory}</td>
                <td><button  onClick={this.handleClick}>Delete</button></td>
            </tr>
        );
    }
}

class TrialTable extends React.Component{
    render() {
        var rows = [];
        var functieStergere=this.props.deleteFunc;
        this.props.trials.forEach(function (at){
           rows.push(<TrialRow trial={at} key={at.id} deleteFunc={functieStergere} />)
        });
        return (
            <div className="TrialTable">

                <table className="center">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>maxNumberOfParticipants</th>
                        <th>trialType</th>
                        <th>ageCategory</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>{rows}</tbody>
                </table>

            </div>
        );
    }
}

export default TrialTable;