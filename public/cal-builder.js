const header = {
	1 : "1 - 8:00am",
	2 : "2 - 9:00am",
	3 : "3 - 10:00am",
	4 : "4 - 11:00am",
	5 : "5 - 12:00pm",
	6 : "6 - 1:00pm",
	7 : "7 - 2:00pm",
	8 : "8 - 3:00pm",
	9 : "9 - 4:00pm",
	10 : "10 - 5:00pm"
}

const days = [ "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"];

function makeCalendar(table, response) {

	var tableBody = document.createElement("tbody");

	var rows = {
		"TERM GRID" : header
	}

	days.forEach((day) => {
		rows[day.toLowerCase()] = {};
		for(var i = 1; i <= 10; i++) {
		rows[day.toLowerCase()][i] = [];
		}
	});
	
	response.students.forEach( (student) => {
		student.classes.forEach( (section) => {
			section.times.forEach( (time) => {
				for(var i = time.start; i <= time.end; i++) {
					var target = rows[time.day][i];
					target.push(student.name + " - " + section.code + "\n");
				}
			});
		});
	});

	var headerRow = document.createElement("tr");
	var headerFirstCell = document.createElement("th");
	headerFirstCell.appendChild(document.createTextNode("TERM GRID"));
	headerRow.appendChild(headerFirstCell);
	for(var i = 1; i <= 10; i++) {
		var headerTableCell = document.createElement("th");
		headerTableCell.appendChild(document.createTextNode(header[i]));
		headerRow.appendChild(headerTableCell);
	}
	tableBody.appendChild(headerRow);

	days.forEach( (day) => {
		var tableRow = document.createElement("tr");
		var firstCell = document.createElement("td");
		firstCell.appendChild(document.createTextNode(day));
		tableRow.appendChild(firstCell);
		for(var i = 1; i <= 10; i++) {
			var tableCell = document.createElement("td");
			rows[day.toLowerCase()][i].forEach( (text) => {
				tableCell.appendChild(document.createTextNode(text));
			});
			tableRow.appendChild(tableCell);
		}
		tableBody.appendChild(tableRow);
	});

	table.appendChild(tableBody);
}