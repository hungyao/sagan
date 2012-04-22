<!DOCTYPE HTML>
<html lang="en">
  <head>
    <title>WebGL Globe</title>
    <meta charset="utf-8">
    <style type="text/css">
      html {
        height: 100%;
      }
      body {
        margin: 0;
        padding: 0;
        background: #000000 url(static/loading.gif) center center no-repeat;
        color: #ffffff;
        font-family: sans-serif;
        font-size: 13px;
        line-height: 20px;
        height: 100%;
      }

      #info {

        font-size: 11px;
        position: absolute;
        bottom: 5px;
        background-color: rgba(0,0,0,0.8);
        border-radius: 3px;
        right: 10px;
        padding: 10px;

      }

      #currentInfo {
        width: 270px;
        position: absolute;
        left: 20px;
        top: 63px;

        background-color: rgba(0,0,0,0.2);

        border-top: 1px solid rgba(255,255,255,0.4);
        padding: 10px;
      }

      a {
        color: #aaa;
        text-decoration: none;
      }
      a:hover {
        text-decoration: underline;
      }

      .bull {
        padding: 0 5px;
        color: #555;
      }

      #title {
        position: absolute;
        top: 20px;
        width: 270px;
        left: 20px;
        background-color: rgba(0,0,0,0.2);
        border-radius: 3px;
        font: 20px Georgia;
        padding: 10px;
      }

      .year {
        font: 16px Georgia;
        line-height: 26px;
        height: 30px;
        text-align: center;
        float: left;
        width: 90px;
        color: rgba(255, 255, 255, 0.4);

        cursor: pointer;
        -webkit-transition: all 0.1s ease-out;
      }

      .year:hover, .year.active {
        font-size: 23px;
        color: #fff;
      }

      #ce span {
        display: none;
      }

      #ce {
        width: 107px;
        height: 55px;
        display: block;
        position: absolute;
        bottom: 15px;
        left: 20px;
        background: url(static/ce.png);
      }


    </style>
  </head>
  <body>

  <div id="container"></div>

  <div id="info">
    <strong><a href="http://www.chromeexperiments.com/globe">Aurora LIVE</a></strong> <span class="bull">&bull;</span> Created by the Google Data Arts Team <span class="bull">&bull;</span> Data acquired from <a href="http://sedac.ciesin.columbia.edu/gpw/">SEDAC</a>
  </div>

  <div id="currentInfo">
    <span id="year1990" class="year">1990</span>
    <span id="year1995" class="year">1995</span>
    <span id="year2000" class="year">2000</span>
  </div>

  <div id="title">
    Aurora LIVE
  </div>

  <a id="ce" href="http://www.chromeexperiments.com/globe">
    <span>This is a Chrome Experiment</span>
  </a>

  <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
  <script type="text/javascript" src="static/third-party/Three/ThreeWebGL.js"></script>
  <script type="text/javascript" src="static/third-party/Three/ThreeExtras.js"></script>
  <script type="text/javascript" src="static/third-party/Three/RequestAnimationFrame.js"></script>
  <script type="text/javascript" src="static/third-party/Three/Detector.js"></script>
  <script type="text/javascript" src="static/third-party/Tween.js"></script>
  <script type="text/javascript" src="static/globe.js"></script>
  <script type="text/javascript">

    if(!Detector.webgl){
      Detector.addGetWebGLMessage();
    } else {

      var years = ['1990','1995','2000'];
      var container = document.getElementById('container');
      var globe = new DAT.Globe(container);
      console.log(globe);
      var i, tweens = [];

      var settime = function(globe, t) {
        return function() {
          new TWEEN.Tween(globe).to({time: t/years.length},500).easing(TWEEN.Easing.Cubic.EaseOut).start();
          var y = document.getElementById('year'+years[t]);
          if (y.getAttribute('class') === 'year active') {
            return;
          }
          var yy = document.getElementsByClassName('year');
          for(i=0; i<yy.length; i++) {
            yy[i].setAttribute('class','year');
          }
          y.setAttribute('class', 'year active');
        };
      };

      for(var i = 0; i<years.length; i++) {
        var y = document.getElementById('year'+years[i]);
        y.addEventListener('mouseover', settime(globe,i), false);
      }

      TWEEN.start();
	/*
      $.getJSON('static/population909500.json', {}, function(data, textStatus) {
    	  window.data = data;
          for (i=0;i<data.length;i++) {
            globe.addData(data[i][1], {format: 'magnitude', name: data[i][0], animated: true});
          }
          globe.createPoints();
          settime(globe,0)();
          globe.animate();
      });
      */
      $.getJSON('aurora/now', {}, function(data, textStatus) {
    	  window.data = data;

    	  var movement = 0.5;
    	  for (var t=0;t<2;t++) {
	    	  var buffer = [];
	    	  
	    	  // Northern hemisphere
	    	  for (i=0;i<data.n.length;i++) {
	    		 for (j=0;j<data.n[i].length;j++) {
	    			if (data.n[i][j] == 0) {
	    				continue;
	    			}
					var lonlat = conv_xy_to_latlong(i, j, true);
					var intensity = data.n[i][j]/50;
					
					var delta = (((i + j + t) % 3) - 1) * movement * intensity;
      
					buffer.push(lonlat[1]);
					buffer.push(lonlat[0]);
					buffer.push(intensity + delta);
	    		 }  
	    	  }
	    	  
	     	  // Southern hemisphere
	    	  for (i=0;i<data.s.length;i++) {
	    		 for (j=0;j<data.s[i].length;j++) {
	    			if (data.s[i][j] == 0) {
	    				continue;
	    			}
					var lonlat = conv_xy_to_latlong(i, j, false);
					var intensity = data.s[i][j]/50;
					
					var delta = (((i + j + t) % 3) - 1) * movement * intensity;
					
					buffer.push(lonlat[1]);
					buffer.push(lonlat[0]);
					buffer.push(intensity + delta);
	    		 }  
	    	  }   	  
	    	  globe.addData(buffer, {format: 'magnitude', animated: true});
    	  }
          globe.createPoints();
          settime(globe,0)();
          globe.animate();
      });
    }

  function conv_xy_to_latlong(x,y,NorS) {
  //NorS: true = north; false = south;

      var latitude;
      var longitude;
      var lower_lat = 34;
      var img_w = 200;
      var img_h = 200;
      
      // 0 < x < img_w maps to -180 < x < 180
      longitude = x * 360 / img_w - 180 ;

      if (NorS) {
          //north
          // y: 0 to 200 maps 90 to 34
          latitude = y * -1 * (90 - lower_lat)/img_h + 90;   
      } else {
          //south
          // y: 0 to 200 maps -34 to -90
          latitude = y * (-90 + lower_lat)/img_h - lower_lat;   
      }

      //console.log('long:'+longitude);
      //console.log('lat:'+latitude);
      return [longitude,latitude];

  }


	$(function() {

		var i = 0;
		var code = function() {
			console.log("at step " + i);
			settime(globe, i % 2);
			i++;
		};
		setInterval(code, 5000);
	});
	</script>

  </body>

</html>
