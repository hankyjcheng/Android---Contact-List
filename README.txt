Yuan-Ju (Hank) Cheng

I used Volley to make JSON request, as suggested in the guideline. Volley JAR file included in libs.
This application has 2 activities (ContactActivity and DetailActivity).

* 	ContactActivity shows the list of contacts in a listview. It is the main activity of the application.
	VolleySingleton is a singleton class that sets up RequestQueue that handles both JsonArrayRequest and JsonObjectRequest.
	In ContactActivity, there is a method makeJsonArrayRequest() that stores a list of contact object that has the attributes name, company, phone, etc through method convertContact(JSONObject object).
	A ContactAdapter class will take that list and used it for the listview.
	NetworkImageView is used for image. It takes a imageLoader that is set up inside VolleySingleton class.

*	Going into DetailActivity, whichever contact is selected will be saved inside DetailActivity, so that it can be used later.
	makeJsonObjectRequest calls from saved contact object's detailsURL.
	All information downloaded from makeJsonObjectRequest and already saved inside contact will be set to textviews.
	NetworkImageView is used here as well.
	
*	There are 3 XML layouts (contact, contact_row, contact_detail)
	contact.xml is the listview that is set up in ContactActivity.
	contact_row.xml is the individual custom listview row that is set up inside ContactAdapter's getView method.
	contact_detail.xml is the layout for DetailActivity, it contains a ScrollView that has multiple RelativeLayout act as table rows
	Each RelativeLayout contains the TextViews and NetworkImageView where they are appropriate. 
