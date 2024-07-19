//modal


console.log("Modal	")

const viewContactModal= document.getElementById("viewContactModal");


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
  id: "viewContactModal",
  override: true
};

const contactModal = new Modal(viewContactModal, options, instanceOptions);

function openContactModal(){
	contactModal.show();
}
