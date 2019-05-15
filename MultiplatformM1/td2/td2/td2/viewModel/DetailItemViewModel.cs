using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Diagnostics;
using System.Text;
using System.Threading.Tasks;
using TD.Api.Dtos;
using Xamarin.Forms;
using Xamarin.Forms.Maps;

namespace td2.viewModel
{
    public class DetailItemViewModel :BaseViewModel
    {
        public Map Map { get; set; }
        public PlaceItem Item { get; set; }

        public Command LoadMap { get; set; }

        public DetailItemViewModel(Map map, PlaceItem item = null)
        {
            LoadMap = new Command(async () => await ExecuteLoadMap());

            Map = map;
            Item = item;

            LoadMap.Execute(null);
        }

        async Task ExecuteLoadMap()
        {
            if (Refresh)
                return;

            Refresh = true;

            var item = await restService.FindPlaceItemById(Item.Id);

            Title = item?.Title;
            Description = item?.Description;

            Item = item;



            Map.Pins.Add(new Pin
            {
                Label = Item.Title,
                Position = new Position(Item.Latitude, Item.Longitude)
            });

            Map.MoveToRegion(MapSpan.FromCenterAndRadius(new Position(Item.Latitude, Item.Longitude), Distance.FromMiles(0.1)));

            Refresh = false;
        }
    }
}
