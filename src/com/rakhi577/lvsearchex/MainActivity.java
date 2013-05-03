package com.rakhi577.lvsearchex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private SeparatedListAdapter sepadapter;
    private ArrayList<HashMap<String, String>> mylist ;
	private HashMap<String, String> map;
	private String Header;
	
	private EditText et;
	private TextView results;
	private Button clear;
	private ListView lv;
	private String[] listview_names = 
		{"Afghanistan","Albania","Algeria","American Samoa","Andorra",
		"Angola","Anguilla","Antigua and Barbuda","Argentina","Armenia",
		"Aruba","Australia","Austria","Azerbaijan","Bahamas",
		"Bahrain","Bangladesh","Barbados","Belarus","Belgium",
		"Belize","Benin","Bermuda","Bhutan","Bolivia",
		"Bosnia-Herzegovina","Botswana","Bouvet Island","Brazil","Brunei",
		"Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon",
		"Canada","Cape Verde","Cayman Islands","Central African Republic","Chad",
		"Chile","China","Christmas Island","Cocos (Keeling) Islands","Colombia",
		"Comoros","Congo, Democratic Republic of the (Zaire)","Congo, Republic of",
		"Cook Islands","Costa Rica",
		"Croatia","Cuba","Cyprus","Czech Republic","Denmark",
		"Djibouti","Dominica","Dominican Republic","Ecuador","Egypt",
		"El Salvador","Equatorial Guinea","Eritrea","Estonia","Ethiopia",
		"Falkland Islands","Faroe Islands","Fiji","Finland","France",
		"French Guiana","Gabon","Gambia","Georgia","Germany",
		"Ghana","Gibraltar","Greece","Greenland","Grenada",
		"Guadeloupe (French)","Guam (USA)","Guatemala","Guinea","Guinea Bissau",
		"Guyana","Haiti","Holy See","Honduras","Hong Kong",
		"Hungary","Iceland","India","Indonesia","Iran",
		"Iraq","Ireland","Israel","Italy","Ivory Coast (Cote D`Ivoire)",
		"Jamaica","Japan","Jordan","Kazakhstan","Kenya",
		"Kiribati","Kuwait","Kyrgyzstan","Laos","Latvia",
		"Lebanon","Lesotho","Liberia","Libya","Liechtenstein",
		"Lithuania","Luxembourg","Macau","Macedonia","Madagascar",
		"Malawi","Malaysia","Maldives","Mali","Malta",
		"Marshall Islands","Martinique (French)","Mauritania","Mauritius","Mayotte",
		"Mexico","Micronesia","Moldova","Monaco","Mongolia",
		"Montenegro","Montserrat","Morocco","Mozambique","Myanmar",
		"Namibia","Nauru","Nepal","Netherlands","Netherlands Antilles",
		"New Caledonia (French)","New Zealand","Nicaragua","Niger","Nigeria",
		"Niue","Norfolk Island","North Korea","Northern Mariana Islands","Norway",
		"Oman","Pakistan","Palau","Panama","Papua New Guinea",
		"Paraguay","Peru","Philippines","Pitcairn Island","Poland",
		"Polynesia (French)","Portugal","Puerto Rico","Qatar","Reunion",
		"Romania","Russia","Rwanda","Saint Helena","Saint Kitts and Nevis",
		"Saint Lucia","Saint Pierre and Miquelon","Saint Vincent and Grenadines",
		"Samoa","San Marino","Sao Tome and Principe","Saudi Arabia","Senegal",
		"Serbia","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia",
		"Solomon Islands","Somalia","South Africa",
		"South Georgia and South Sandwich Islands","South Korea","Spain",
		"Sri Lanka","Sudan","Suriname","Svalbard and Jan Mayen Islands","Swaziland",
		"Sweden","Switzerland","Syria","Taiwan","Tajikistan",
		"Tanzania","Thailand","Timor-Leste (East Timor)","Togo","Tokelau",
		"Tonga","Trinidad and Tobago","Tunisia","Turkey","Turkmenistan",
		"Turks and Caicos Islands","Tuvalu","Uganda","Ukraine","United Arab Emirates",
		"United Kingdom","United States","Uruguay","Uzbekistan","Vanuatu",
		"Venezuela","Vietnam","Virgin Islands","Wallis and Futuna Islands","Yemen",
		"Zambia","Zimbabwe"};
	
	private List<String> array_sort;
	int textlength=0;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/**
		 * Initialization Part
		 */
		et 			= 	(EditText) findViewById(R.id.edt__searchby);
		lv			=	(ListView) findViewById(android.R.id.list);
		results		=	(TextView) findViewById(R.id.txt__results);
		clear		=	(Button)findViewById(R.id.btn__cancel);
		array_sort	=	new ArrayList<String>();
		sepadapter 	= 	new SeparatedListAdapter(this);
		
		/**
		 * Appending the listview items by index wise.Here the loop performs with all characters from 
		 * 'A' to 'Z'.
		 * Adding the header if the name with the specific character exists.
		 * you can check there are no items with 'X' exists. so the 'X' is not append to list.
		 * This is achived with the simple logic of boolean flag value.
		 */
		for(char ch='A';ch<='Z';ch++)
		{
			boolean flag = false;
			mylist = new ArrayList<HashMap<String, String>>();
			Header = Character.toString(ch);
			array_sort.add(Header);
			for(int j=0;j<listview_names.length;j++)
			{
				if(listview_names[j].startsWith(Header))
				{
					flag = true;
					map = new HashMap<String, String>();
					array_sort.add(listview_names[j]);
    				map.put("country", listview_names[j]);
    				mylist.add(map);
				}
			}
			if(flag)
			{
				ListAdapter adapter = new SimpleAdapter(MainActivity.this, mylist, R.layout.search_list_item,
						  new String[]{"country"},new int[]{R.id.title});
				sepadapter.addSection(Header, adapter);
			}
			else
			{
				array_sort.remove(Header);
			}
			
		}
		
		/**
		 * Append adapter to listview
		 */
		results.setText(listview_names.length+" results found");
		lv.setAdapter(sepadapter);
		lv.setFastScrollEnabled(true);
		
		/**
		 * Button functionality to clear the entered text in EditText
		 */
		clear.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) 
			{
				et.setText("");
			}
		});
		
		
		/**
		 * This is the heart of this Application.
		 * Using "addTextChangedListener" for EditText we can make wonders.
		 * This is the simple example with this feature.
		 * 
		 * 
		 * 
		 * Here there is no need to Sort the listview items. Instead we can populate listitems which matches
		 * with the entered text in the EditText
		 */
		et.addTextChangedListener(new TextWatcher()
		{
			public void afterTextChanged(Editable s)
			{
                  // Abstract Method of TextWatcher Interface.
			}
			public void beforeTextChanged(CharSequence s,
					int start, int count, int after)
			{
				// Abstract Method of TextWatcher Interface.
			}
			public void onTextChanged(CharSequence s,
	                int start, int before, int count)
	        {
	            textlength = et.getText().length();
	            for (int i = 0; i < array_sort.size(); i++)
	            {
	                if (textlength <= array_sort.get(i).length())
	                {
	                    if(array_sort.get(i).toLowerCase().contains(
	                            et.getText().toString().toLowerCase().trim()))
	                    {
	                    	lv.setSelection(i);//Everything works here
	                    	break;
	                    }
	                  }
	            }
             }
	       });
		
		/**
		 * Click on listview item will show the toast message with the item
		 */
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0,
			                    View arg1, int position, long arg3)
			{
			   Toast.makeText(getApplicationContext(), array_sort.get(position),
					   Toast.LENGTH_SHORT).show();
			}
		});
	}
}