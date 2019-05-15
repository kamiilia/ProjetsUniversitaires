using Plugin.Media;
using Plugin.Media.Abstractions;
using Plugin.Permissions;
using Plugin.Permissions.Abstractions;
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
	public partial class AddItemPage : ContentPage
	{
        AddItemViewModel addItemViewModel;
        MediaFile selectedImageFile;

        public AddItemPage ()
		{
			InitializeComponent ();
            BindingContext = addItemViewModel = new AddItemViewModel(map);
		}

        async void Cancel_Clicked(object sender, EventArgs e)
        {
            await Navigation.PopModalAsync();
        }

        async void Save_Clicked(object sender, EventArgs e)
        {
            if (await addItemViewModel.AddPlaceItem(selectedImageFile))
            {
                MessagingCenter.Send(this, "addItem");

                await Navigation.PopModalAsync();
            }
        }

        void Button_Clicked(object sender, EventArgs e)
        {
            addItemViewModel.Button_Clicked();
        }

        async void Handle_Clicked(object sender, EventArgs e)
        {
            await CrossMedia.Current.Initialize();

            if (!CrossMedia.Current.IsPickPhotoSupported)
            {
                await DisplayAlert("Not supported", "Your device does not currently support this functionality", "Ok");
                return;
            }

            var selectedImageFile = await CrossMedia.Current.PickPhotoAsync(new PickMediaOptions
            {
                PhotoSize = PhotoSize.Medium
            });

            if (selectedImage == null)
            {
                await DisplayAlert("Error", "Could not get the image, please try again.", "Ok");
                return;
            }

            if (selectedImageFile != null)
            {
                this.selectedImageFile = selectedImageFile;
                selectedImage.Source = ImageSource.FromStream(() => selectedImageFile.GetStream());
            }
        }

        async void Camera_Clicked(object sender, EventArgs e)
        {
            await CrossMedia.Current.Initialize();

            if (!CrossMedia.Current.IsTakePhotoSupported)
            {
                await DisplayAlert("No camera", "No camera available", "Ok");
                return;
            }

            var ImageFile = await CrossMedia.Current.TakePhotoAsync(new StoreCameraMediaOptions
            {
                Directory = "Pictures",
                Name = "sample.jpg"
            });

            if (selectedImage == null)
            {
                await DisplayAlert("Error", "Could not get the image, please try again.", "Ok");
                return;
            }

            if (ImageFile != null)
            {
                selectedImage.Source = ImageSource.FromStream(() => ImageFile.GetStream());
            }
        }
    }
}