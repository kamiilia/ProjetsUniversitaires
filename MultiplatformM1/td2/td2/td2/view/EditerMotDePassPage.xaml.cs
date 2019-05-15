using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using td2.viewModel;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace td2.view
{
	[XamlCompilation(XamlCompilationOptions.Compile)]
	public partial class EditerMotDePassPage : ContentPage
	{
        EditerMotDePassViewModel editerMotDePassViewModel;

		public EditerMotDePassPage ()
		{
			InitializeComponent ();
            BindingContext = editerMotDePassViewModel = new EditerMotDePassViewModel();

        }
        async void updatepassword(object sender, EventArgs e)
        {
            if (await editerMotDePassViewModel.Update())
            {
                await Navigation.PopAsync();
            }
        }

    }
}