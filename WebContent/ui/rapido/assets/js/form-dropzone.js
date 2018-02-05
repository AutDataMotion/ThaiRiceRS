var handleDropFileUpload = function (divfile, urlup, afilenamediv) {
        var dropzone_file = new Dropzone(divfile, {
            url: urlup,
            paramName: "file",
            maxFilesize: 10, // MB
            init: function () {
                this.on("addedfile", function (file) {
                    // Create the remove button
                    var removeButton = Dropzone.createElement("<a href='javascript:;'' class='btn red btn-sm btn-block'>删除</a>");
                    // Capture the Dropzone instance as closure.
                    var _this = this;

                    // Listen to the click event
                    removeButton.addEventListener("click", function (e) {
                        // Make sure the button click doesn't submit the form:
                        e.preventDefault();
                        e.stopPropagation();
                        // Remove the file preview.
                        _this.removeFile(file);
                        // If you want to the delete the file on the server as well,
                        // you can do the AJAX request here.
                    });

                    // Add the button to the file preview element.
                    file.previewElement.appendChild(removeButton);
                });
				this.on("success",function(file, rspdata){
					//afilenamediv = rspdata[0]["fileName"];
					$(afilenamediv).attr("value",rspdata);
					//fileupload = rspdata[0]["ids"];

				});
            }//end init
        });
    }
	
