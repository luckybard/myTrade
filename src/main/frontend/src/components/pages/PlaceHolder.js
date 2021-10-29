
import TextSearch from './../ad/TextSearch';
import RandomAd from './../ad/RandomAd';

const PlaceHolder = () => {
    return (
      <div>
        <h1>Place Holder</h1>
        <TextSearch text="t-shirt" />
      <h1>Categories to Click</h1>
      <h1>Random Ads / Last visited category ads</h1>
      <RandomAd />
      </div>
    );
  };
  
  export default PlaceHolder;