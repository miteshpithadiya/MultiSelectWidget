# MultiSelectWidget 

A widget for selecting multiple items from a list.

![Alt text](https://github.com/miteshpithadiya/MultiSelectWidget/blob/master/multiselectwidget/src/main/res/multiselectwidget.gif "MultiSelectWidget")

# Gradle
    dependencies {
        ...
        compile 'com.toptoche.multiselectwidget:multiselectwidget:0.0.3'
    }

# Usage
        <com.toptoche.multiselectwidget.MultiSelectView
          android:layout_width="wrap_content"
          android:layout_height="wrap_content" />

        multiSelectView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(android.R.array.imProtocols)), new ArrayList());
                
# Methods
        multiSelectView.setDelimiter(";");
        multiSelectView.setTitle("Select Item");
        
        multiSelectView.setPositiveButton("OK");
        multiSelectView.setPositiveButton("OK", new MultiSelectFragment.OnPositiveButtonClicked() {
            @Override
            public void onPositiveButtonClicked(Dialog dialog) {
                
            }
        });
        
        multiSelectView.setNegativeButton("CANCEL");
        multiSelectView.setNegativeButton("CANCEL", new MultiSelectFragment.OnNegativeButtonClicked() {
            @Override
            public void onNegativeButtonClicked(Dialog dialog) {
                
            }
        });
        
        multiSelectView.setOnNoItemSelectedListener(new MultiSelectFragment.OnNoItemSelected() {
            @Override
            public void onNoItemSelected(Dialog dialog) {
                
            }
        });
    
# Changelog
 * <b>0.0.3</b>
    * Bug Fixes.
 * <b>0.0.2</b>
    * Resolved issues related to fetching selected items.
 * <b>0.0.1</b>
    * Initial Release

# License

    Copyright 2015-2016 Mitesh Pithadiya

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
