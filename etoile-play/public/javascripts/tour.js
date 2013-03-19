jQuery(function($) {
  var tour = new Tour({onEnd: function (tour) {jQuery.post("/olduser");} });
  tour.addStep({
  element: "#stats", /* html element next to which the step popover should be shown */
  title: "Welcome", /* title of the popover */
  content: "This area presents an overview of your etoile statistics." /* content of the popover */
});
  tour.addStep({
	  path: "/index",
	  element: "#profilemenu", /* html element next to which the step popover should be shown */
	  title: "Profile menu", /* title of the popover */
	  content: "Here you can complete your etoile profile." /* content of the popover */
	});
  tour.addStep({
	  element: "#modulesmenu", /* html element next to which the step popover should be shown */
	  title: "My Modules", /* title of the popover */
	  content: "This area presents the list of the modules where you signed up." /* content of the popover */
	});
  tour.addStep({
	  element: "#topbarmodules", /* html element next to which the step popover should be shown */
	  title: "Sign up in new modules", /* title of the popover */
	  placement: "bottom",
	  content: "To sign up in new modules, click here and you will see all the modules available." /* content of the popover */
	});
  tour.addStep({
	  element: "#profilemenu", /* html element next to which the step popover should be shown */
	  title: "Let's start using etoile", /* title of the popover */
	  content: "To complete your profile click on My Profile, on the left.", /* content of the popover */
	  reflex: true,
  });
  tour.addStep({
	  path: "/myprofile",
	  element: "#editProfile", /* html element next to which the step popover should be shown */
	  title: "Profile", /* title of the popover */
	  content: "Click here to edit your informations.", /* content of the popover */
		  reflex: true
	});
  tour.addStep({
	  path: "/myprofile",
	  element: "#editAboutProfile", /* html element next to which the step popover should be shown */
	  title: "Modules menu", /* title of the popover */
	  content: "Now let's edit your education information.", /* content of the popover */
	  reflex: true
	});
  tour.addStep({
	  path: "/myprofile",
	  element: "#topbarmodules", /* html element next to which the step popover should be shown */
	  title: "Let's sign up a module", /* title of the popover */
	  placement: "bottom",
	  content: "Now click here to check out what modules there are in etoile, and apply in one of your choice.", /* content of the popover */
	  reflex: true
	});
  tour.addStep({
	  path: "/modules",
	  element: "#modules", /* html element next to which the step popover should be shown */
	  title: "Modules", /* title of the popover */
	  content: "This is the modules list, click on any of this modules to get more informations about them and sign up.", /* content of the popover */
	  placement: "left",
		  reflex:true
	});

  tour.start();

  $(".restart").click(function (e) {
    e.preventDefault();
    tour.restart();
    $(this).parents(".alert").alert("close");
  });
});