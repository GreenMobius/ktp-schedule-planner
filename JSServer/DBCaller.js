function parseICS(file){
    var input = file.target;

    var reader = new FileReader();
    reader.onload = function(){
      var arrBuf = reader.result;
      //var output = document.getElementById('output');
      //output.src = dataURL;
      console.log(dataURL)
      console.log(arrayBuffer.byteLength);
    };
    reader.readAsArrayBuffer(input.files[0]);
    console.log(read)
  };

var openFile = function(event) {
    console.log("here")
    var input = event.target;
    var coveredSections = [];
    var reader = new FileReader();
    var textResults;
    reader.onload = function(){
        console.log("onload")
        textResults = reader.result;
        console.log("textResults")

        console.log(textResults);
        let arrayOfLines = textResults.match(/[^\r\n]+/g);
        console.log(arrayOfLines.length);
        var index = 0;
        var myBuffer = [];
        while(index < arrayOfLines.length){
            var line = arrayOfLines[index]
            if(line == "BEGIN:VEVENT") {
                console.log("recording event ... ")
                line = arrayOfLines[index]
                console.log(line)
                while(line != "END:VEVENT" && index < arrayOfLines.length) {
                    myBuffer.push(arrayOfLines[index]); 
                    index = index + 1  
                    line = arrayOfLines[index]
                }
                console.log(myBuffer.length);
                if(!(myBuffer.length == 8 || myBuffer.length == 9 ||  myBuffer.length == 10) ){
                    console.log('error in ics file')
                }
                coveredSections.push(AddSectionToDBFromCalendar(myBuffer));
                myBuffer = []
            }
            index = 1+index;
        }
        console.log(coveredSections)
        return coveredSections;
    };
    var text = reader.readAsText(input.files[0]);

};

function AddSectionToDBFromCalendar(section) {
    console.log("section",section)
    //There should be 7 lines of information that are used to decode the .ics file
    var data = []
    var summaryClass = section[1].replace("SUMMARY:", ""); //get rid of the "SUMMARY:" start

    var attendanceAndProfInfo = section[2]; //TODO: figure out how to get this information together
    var splitSections = attendanceAndProfInfo.split(";");
    var profName = splitSections[2].split(":")[1];

    var location = section[3].replace("LOCATION:", "");

    var startTime = section[4].replace("DTSTART:", "");
    var index = 5
    if(!section[5].includes(":")){
        startTime.concat(section[5])
        index = index+1
    }
    var endTime = section[index].replace("DTEND:", "");
    index = index+1
    if(!section[index].includes(":")){
        startTime.concat(section[5])
        index = index+1
    }
    startTime = startTime.substring(startTime.indexOf("T")+1);
    endTime = endTime.substring(startTime.indexOf("T")+1);

    var quarterAndYear = section[index].replace("CATEGORIES:","");

    var specificInfo = section[index];//should be basically a repeat of summaryClass, not sure we need this but I'll leave it here in case
    

    var prof = attendanceAndProfInfo.substring(attendanceAndProfInfo.indexOf(":") + 1);

    return {'SUMMARY' : summaryClass, 'PROFNAME': profName, "LOCATION": location, "STARTTIME": startTime, "ENDTIME": endTime, "QUARTERANDYEAR": quarterAndYear, "SPECIFICNFO": specificInfo, "PROF": prof}
};