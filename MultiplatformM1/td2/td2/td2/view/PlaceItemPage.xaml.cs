using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TD.Api.Dtos;
using td2.viewModel;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace td2.view
{
	[XamlCompilation(XamlCompilationOptions.Compile)]
	public partial class PlaceItemPage : ContentPage
	{
        PlaceItemViewModel placeItemViewModel;

		public PlaceItemPage ()
		{
			InitializeComponent ();
            BindingContext = placeItemViewModel = new PlaceItemViewModel();
		}

        protected override void OnAppearing()
        {
            base.OnAppearing();

            if (placeItemViewModel.Items.Count == 0)
                placeItemViewModel.LoadCommand.Execute(null);
        }
        async void OnItemSelected(object sender, SelectedItemChangedEventArgs args)
        {
            var item = args.SelectedItem as PlaceItemSummary;
            if (item == null)
                return;
            PlaceItem place = await placeItemViewModel.restService.FindPlaceItemById(item.Id);
            await Navigation.PushAsync(new ItemDetailPage(place));

            // Manually deselect item.
            ItemsListView.SelectedItem = null;
        }
        async void Ajouter_Lieu(object sender, EventArgs a)
        {
            await Navigation.PushModalAsync(new NavigationPage(new AddItemPage()));
        }
    }
}