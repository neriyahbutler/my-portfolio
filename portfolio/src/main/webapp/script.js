// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

var userLoggedIn;

function getLink(){
    console.log("running getLink()...");
    fetch("/log").then(response => response.json()).then((text) => {

      console.log(text);
      const jsonValues = Object.values(text);

      var logUrl = jsonValues[0];
      userLoggedIn = jsonValues[1];
      
      var loginLink = document.getElementById("loginLink");
      loginLink.href = logUrl;

      var loginStatus = document.getElementById("loginDescription");

      if(userLoggedIn == "true"){ loginStatus.innerHTML = "LOG OUT HERE";}
      else{ loginStatus.innerHTML = "LOG IN HERE";} 
    });
}


function getComments(){
  console.log("running getComments()...");
  getLink();
  fetch('/data').then(response => response.json()).then((text) => {
    const commentContainer = document.getElementById('commentList');
    const jsonValues = Object.values(text);
    const jsonKeys = Object.keys(text);

    console.log(jsonValues);
    console.log("User is logged in: " + userLoggedIn);
    var commentStatus = document.getElementById("commentStatus");


    if(userLoggedIn == "true"){
      for(var i = 0; i < jsonValues.length; i++){
        var comment = document.createElement('div');
        comment.className = 'comment';

        // commentMain is for the actual comment itself
        var commentMain = document.createElement('div');
        commentMain.className = 'commentMain';
        // commentDetails is for the name of the commenter
        var commentDetails = document.createElement('div');
        commentDetails.className = 'commentDetails';

        commentMain.innerHTML = "\"" + jsonValues[i] + "\"";
        commentDetails.innerHTML = jsonKeys[i];

        comment.appendChild(commentMain);
        comment.appendChild(commentDetails);
        commentContainer.appendChild(comment);

        commentStatus.innerHTML = "COMMENTS";
        }
      }
      else{
        commentStatus.innerHTML = "LOGIN TO SEE COMMENTS";
    }  
  });
}

