import Ad from "./Ad";

const AdList = (props) => {
  return (
    <ul>
      {props.ads.map((ad) => (
        <Ad  key={ad.id} title={ad.title} description={ad.description}/>
      ))}
    </ul>
  );
};

export default AdList;