console.log("Theme Changer")

let currTheme=gettheme();
changeTheme()
console.log(currTheme)




function changeTheme(){
    //set to web page
    document.querySelector('html').classList.add(currTheme)

    // set listner to button 
    const button =document.querySelector("#theme_change");
    button.addEventListener("click",(event)=>{
        const oldTheme= currTheme
        console.log("button clicked");
        if(currTheme==="dark"){
            currTheme="light"
        }else{
            currTheme="dark"
        }

        //local storage me upload
        setTheme(currTheme)
        //remove current theme
        document.querySelector('html').classList.remove(oldTheme)
        //set new th
        document.querySelector('html').classList.add(currTheme)


        //change the text button

        button.querySelector('span').textContent = currTheme=='light'?'Dark':"Light";
    });

}
//  set theme to lacle staorage
function setTheme(theme){
    localStorage.setItem("theme",theme);
}
function gettheme(){
    let theme= localStorage.getItem("theme");
     return theme ? theme : "light";
}

//  get theme fro local storage



console.log("Modal");
//modal


console.log("Modal	")

const viewContactModal= document.getElementById("view_contact_modal");


// options with default values
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
  id: "view_contact_modal",
  override: true
};

const contactModal = new Modal(viewContactModal, options, instanceOptions);

function openContactModal(){
	contactModal.show();
}
function closeContactModal(){
	contactModal.hide();
}

 async function loadContactData(id){
	// load data
	console.log(id);
	try{
		const data =await ( await fetch(`http://localhost:8080/api/contacts/${id}`)).json();
		console.log(data);
		document.querySelector("#contact_name").innerHTML=data.name;
		document.querySelector("#contact_email").innerHTML=data.email;
		document.querySelector("#contact_image").src = data.picture;
		document.querySelector("#contact_phone").innerHTML=data.phone
        document.querySelector("#contact_address").innerHTML=data.address;
        document.querySelector("#contact_about").innerHTML=data.descr;
        const contactFavorite = document.querySelector("#contact_favorite");
        if (data.favBoolean) {
        contactFavorite.innerHTML =
        "<i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i>";
         } else {
         contactFavorite.innerHTML = "Not Favorite Contact";
        }
        document.querySelector("#contact_website").href=data.websiteLink;
        document.querySelector("#contact_website").innerHTML=data.websiteLink;
        document.querySelector("#contact_linkedIn").href=data.linkLink;
        document.querySelector("#contact_linkedIn").innerHTML=data.linkLink;
		openContactModal();
	}catch(error){
		console.log(error);
	}
	
}




//image viewer

document.querySelector("#image_file_input").addEventListener('change',function(event){
	var file=event.target.files[0];
	var reader=new FileReader();
	reader.onload=function(){
		document.getElementById("upload_image_id").src= reader.result;
	};
	reader.readAsDataURL(file);
	
	
});



//delete contact alert

async function deleteContact(id){
Swal.fire({
  title: "Are you sure?",
  text: "You won't be able to revert this!",
  icon: "warning",
  showCancelButton: true,
  confirmButtonColor: "#3085d6",
  cancelButtonColor: "#d33",
  confirmButtonText: "Yes, delete it!"
}).then((result) => {
  if (result.isConfirmed) {
	const url="http://localhost:8080/user/contact/contacts/delete/"+id;
	window.location.replace(url);
    Swal.fire({
        title: "Deleted!",
        text: "Your file has been deleted.",
        icon: "success",
        confirmButtonColor: "#3085d6",
        timer: 3000, // Adjust the time duration in milliseconds (e.g., 3000 for 3 seconds)
        timerProgressBar: true, // Show a progress bar indicating the time remaining
        showConfirmButton: false // Hide the "OK" button
      }).then(() => {
        // Redirect to the URL after the success message has been displayed
        window.location.replace(url);
      });
    }
  });
}




