import React from 'react';
import { subscribe, getErrors } from 'utils/errorBroker';

class ErrorPanel extends React.Component {
  constructor(props) {
    super(props);
    this.state = { text: [] };
  }

  componentDidMount() {
    this.setState({ text: getErrors() });
    subscribe((error) => {
      const { text } = this.state;
      text.push(error);
      this.setState({ text });
    });
  }

  render() {
    const { text } = this.state;
    const errorParagraphs = text.map((e, i) => (
      // eslint-disable-next-line react/no-array-index-key
      <p key={`error_${i}`} className="error_message">
        {e.toString()}
      </p>
    ));
    return (
      <div className="error_panel">
        {errorParagraphs}
      </div>
    );
  }
}

export default ErrorPanel;
