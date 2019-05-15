using Plugin.Media.Abstractions;
using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Threading.Tasks;
using TD.Api.Dtos;
using Xamarin.Essentials;
using Xamarin.Forms;
using Xamarin.Forms.Maps;
using Map = Xamarin.Forms.Maps.Map;

namespace td2.viewModel
{
    public class AddItemViewModel : BaseViewModel
    {
        public PlaceItem PlaceItem { get; set; }
        Map map;

        public Command LoadPin { get; set; }

        public AddItemViewModel(Map map)
        {
            PlaceItem = new PlaceItem();
            this.map = map;

            LoadPin = new Command(async () => await RunPin());
            LoadPin.Execute(null);
        }

        async Task RunPin()
        {
            map.Pins.Add(new Pin
            {
                Label = ""
            });

            var locator = await Geolocation.GetLastKnownLocationAsync();
            map.Pins[0].Position = new Position(locator.Latitude, locator.Longitude);

            map.MoveToRegion(MapSpan.FromCenterAndRadius(new Position(map.Pins[0].Position.Latitude, map.Pins[0].Position.Longitude), Distance.FromMiles(0.5)));
        }

        public async Task<bool> AddPlaceItem(MediaFile selectedImage)
        {
            PlaceItem.Latitude = map.Pins[0].Position.Latitude;
            PlaceItem.Longitude = map.Pins[0].Position.Longitude;

            if (selectedImage != null)
            {
                while (File.ReadAllBytes(selectedImage.Path).Length == 0)
                {
                    System.Threading.Thread.Sleep(1);
                }

                var image = File.ReadAllBytes(selectedImage.Path);

                ImageItem imageItem = await restService.PostImage(image);
                PlaceItem.ImageId = imageItem.Id;
            }

            return await Task.FromResult(await restService.PostPlaces(PlaceItem));
        }

        public void Button_Clicked()
        {
            map.Pins[0].Position = map.VisibleRegion.Center;
        }
    }
}
