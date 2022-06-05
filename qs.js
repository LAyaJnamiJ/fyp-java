const qs = require('qs');
const query = qs.stringify({
  filters: {
    x: {
      $eq: 1,
    },
    y: {
      $eq: 1,  
    },
    z: {
      $eq: 1,  
    }
  },
}, {
  encodeValuesOnly: true,
});

await request(`http://localhost:1337/api/coefficients?${query}`);