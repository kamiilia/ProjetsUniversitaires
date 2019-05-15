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
	public partial class ItemDetailPage : ContentPage
	{
        DetailItemViewModel viewModel;
        PlaceItem placeItem;

        public ItemDetailPage(PlaceItem item)
		{
            placeItem = item;
			InitializeComponent ();
            BindingContext = this.viewModel = new DetailItemViewModel(map, item);

        }
        public ItemDetailPage()
        {
            InitializeComponent();

            var item = new PlaceItem
            {
                Title = "Item 1",
                
                Description = "This is an item description."
            };
            placeItem = item;
            viewModel = new DetailItemViewModel(map, item);
            BindingContext = viewModel;
        }

        async void Comment_Clicked(object sender, EventArgs e)
        {
            await Navigation.PushAsync(new CommentsPage(new CommentsViewModel(placeItem)));
        }
    }
}