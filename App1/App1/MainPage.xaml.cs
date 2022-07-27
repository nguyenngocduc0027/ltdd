using Firebase.Database;
using Firebase.Database.Query;
using Newtonsoft.Json;
using ServiceStack.Configuration;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;


namespace App1
{
    public partial class MainPage : ContentPage
    {
        Boolean turnOnGr = true;
        Boolean turnOnYl = true;
        Boolean turnOnR = true;

        String myStringProperty;
        int val = 0;

        FirebaseClient firebaseClient = new FirebaseClient("https://mobileiot-8a1a4-default-rtdb.firebaseio.com/");


        public MainPage()
        {
            InitializeComponent();
            BindingContext = this;
        }

        



        public string MyStringProperty
        {
            get { return myStringProperty; }
            set
            {
                myStringProperty = value;
                OnPropertyChanged(nameof(MyStringProperty)); // Notify that there was a change on this property
            }
        }

        private void Button_Clicked_Tru(object sender, EventArgs e)
        {
            if (val >= 36) { val -= 36; }
            /*NhietDoText = val.ToString();*/
            firebaseClient.Child("Servo").PutAsync(val);

        }

        private void Button_Clicked_Cong(object sender, EventArgs e)
        {
            if (val <= 324) { val += 36; }
            /*NhietDoText = val.ToString();*/
            firebaseClient.Child("Servo").PutAsync(val);
        }

        void GreenClicked(object sender, EventArgs e)
        {

            if (turnOnGr == true)
            {
                turnOnGr = false;
                gr.BackgroundColor = Color.Gray;

            }
            else if (turnOnGr == false)
            {
                turnOnGr = true;
                gr.BackgroundColor = Color.Green;

            }
            MyStringProperty = turnOnGr.ToString();

            firebaseClient.Child("Green").PutAsync(turnOnGr);
        }

        void YellowClicked(object sender, EventArgs e)
        {
            if (turnOnYl == true)
            {
                turnOnYl = false;
                ylow.BackgroundColor = Color.Gray;

            }
            else if (turnOnYl == false)
            {
                turnOnYl = true;
                ylow.BackgroundColor = Color.Yellow;

            }
            MyStringProperty = turnOnYl.ToString();
            firebaseClient.Child("Yellow").PutAsync(turnOnYl);
        }

        void RedClicked(object sender, EventArgs e)
        {
            if (turnOnR == true)
            {
                turnOnR = false;
                r.BackgroundColor = Color.Gray;

            }
            else if (turnOnR == false)
            {
                turnOnR = true;
                r.BackgroundColor = Color.Red;

            }
            MyStringProperty = turnOnR.ToString();
            firebaseClient.Child("Red").PutAsync(turnOnR);
        }

        
    }

}
