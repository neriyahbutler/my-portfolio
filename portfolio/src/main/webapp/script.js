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
var username;
var adminPrivileges;
var commentStorage;

// Fetches login/logout link along with login status
function getLink(){
  fetch("/log").then(response => response.json()).then((logInfo) => {
    console.log(logInfo);
    const jsonValues = Object.values(logInfo);

    var logUrl = logInfo.logUrl;
    userLoggedIn = logInfo.isUserLoggedIn;
    username = logInfo.username;
      
    var loginLink = document.getElementById("loginLink");
    loginLink.href = logUrl;

    var loginStatus = document.getElementById("loginDescription");
    if(userLoggedIn == "true"){ 
      var loginStatusContainer = document.getElementById("loginStatus");
      var usernameStatus = document.createElement('p');
      var usernameStorage = document.getElementById("nameStorage");

      usernameStatus.innerHTML = "Logged in as: " + username;
      loginStatusContainer.appendChild(usernameStatus);
      usernameStorage.value = username;

      if(username == "nzbutler" || username == "neriyahbutler21"){
        adminPrivileges = true;
        }else{ adminPrivileges = false;}

      console.log("input storage is: " + usernameStorage.value);

      loginStatus.innerHTML = "LOG OUT HERE";
    }
    else{ loginStatus.innerHTML = "LOG IN HERE";} 
  });
}

// Gathers all the comments from the json object and puts them on the website
function getComments(){
  getLink();

  fetch('/data').then(response => response.json()).then((text) => {
    console.log(text);
    const jsonValues = Object.values(text);
    jsonValues.forEach((task) => {
      console.log(task);
      if(userLoggedIn == "true"){
        const commentsContainer = document.getElementById('commentList');
        var comment = document.createElement('div');
        comment.className = 'comment';

        var commentInfo = document.createElement('div');
        commentInfo.className = 'commentInfo';

        var commentMain = document.createElement('div');
        commentMain.className = 'commentMain';

        var commentDetails = document.createElement('div');
        commentDetails.className = 'commentDetails';

        commentMain.innerHTML = "\"" + task.comment + "\"";
        commentDetails.innerHTML = task.username;

        commentInfo.appendChild(commentMain);
        commentInfo.appendChild(commentDetails);
        comment.appendChild(commentInfo);

        if(adminPrivileges == true){
          var deleteButton = document.createElement('button')
                
          deleteButton.className = "deleteButton";
          deleteButton.innerHTML = "X";
          deleteButton.addEventListener('click', () => {
          deleteTask(task);
          comment.remove();
          })

        comment.appendChild(deleteButton);
        }

      console.log("admin: " + adminPrivileges);

      commentsContainer.appendChild(comment);
      commentStatus.innerHTML = "COMMENTS";
    }
    else{
      commentStatus.innerHTML = "LOGIN TO SEE COMMENTS";
    }
  })
  });
}

function deleteTask(task){
  const params = new URLSearchParams();
  params.append('id', task.id);
  fetch('/delete-tasks', {method: 'POST', body: params});
}

