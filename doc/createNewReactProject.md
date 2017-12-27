# 1.Install babel & babel-cli globally (we’ll get to this)
npm install -g babel
npm install -g babel-cli

# 2. Create your root folder and initialize it. Init is what sets up your package.json file.
mkdir react-setup-tutorial // switch out to your file name here
npm init

# 3. Add Webpack which compiles all the JS and JSX files, also add your react/react-dom libraries.
npm install webpack --save
npm install webpack-dev-server --save
npm install react --save
npm install react-dom --save

# 4. Make sure Babel is installed so you can work with ES6 and beyond!
npm install babel-core --save
npm install babel-loader --save
npm install babel-preset-react --save
npm install babel-preset-es2015 --save

# 5. Make some files to get started with your HTML/JS/JSX/React
touch index.html App.js index.js webpack.config.js

#6. Set up the webpack.config.js file
var config = {
   entry: './index.js',
   output: {
      path:'/',
      filename: 'bundle.js',
   },
   devServer: {
      inline: true,
      port: 8080
   },
   module: {
     loaders: [
       {
         test: /\.jsx?$/,
         exclude: /node_modules/,
         loader: 'babel-loader',
       query: {
         presets: ['es2015', 'react']
            }
         }
      ]
   }
}
module.exports = config;

# 7. Tell your server how to start
// Add this after your "test" in package.json
"start": "webpack-dev-server --hot"

# 8. Set your root element in your index.html file
<!-- index.html -->
<!DOCTYPE html>
<html lang = "en-US">
  <head>
    <meta charset = "UTF-8">
    <title>React App</title>
  </head>
  <body>
    <div id="app"></div>
    <script src="bundle.js"></script>
  </body>
</html>

# 9. Add App.js component & index.js file to kick things off
// App.js
import React from 'react';

class App extends React.Component {
   render() {
      return (
         <div>
            Hey React Do!
         </div>
      );
   }
}

export default App;
// index.js
import React from 'react';
import ReactDOM from 'react-dom';
import App from './App.js';

ReactDOM.render(<App />, document.getElementById('app'));