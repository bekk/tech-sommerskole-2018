import React from 'react';
import { getErrors, subscribe } from 'utils/errorBroker';
import 'styles/errorPanel.less';

class ErrorPanel extends React.Component {
  constructor(props) {
    super(props);
    this.state = { text: [] };
    this.clear.bind(this);
  }

  componentDidMount() {
    this.setState({ text: getErrors() });
    subscribe((error) => {
      const { text } = this.state;
      text.push(error);
      this.setState({ text });
    });
  }

  clear() {
    this.setState({ text: [] });
  }

  render() {
    const { text } = this.state;
    if (!text || !text.length) return null;
    const errorParagraphs = text.map((e, i) => (
      // eslint-disable-next-line react/no-array-index-key
      <p key={`error_${i}`} className="error_message">
        {e.toString()}
      </p>
    ));
    return (
      <div className="error_panel">
        <button
          className="error_panel_close_button"
          onClick={() => this.clear()}
        >
          x
        </button>
        {errorParagraphs}
      </div>
    );
  }
}

export default ErrorPanel;
