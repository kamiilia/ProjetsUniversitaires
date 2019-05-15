using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Diagnostics;
using System.Text;
using System.Threading.Tasks;
using TD.Api.Dtos;
using td2.view;
using Xamarin.Forms;

namespace td2.viewModel
{

    class PlaceItemViewModel :BaseViewModel
    {
    
        public ObservableCollection<PlaceItemSummary> Items { get; set; }
        public Command LoadCommand { get; set; }
       
        public PlaceItemViewModel()

        {
            Items = new ObservableCollection<PlaceItemSummary>();
            LoadCommand = new Command(async () => await ExecuteCommand());

            MessagingCenter.Subscribe<AddItemPage>(this, "addItem", (obj) =>
            {
                LoadCommand.Execute(null);
            });

        }

        public async Task ExecuteCommand()
        {
            if (Refresh)
                return;

            Refresh = true;

            try
            {
                Items.Clear();
                var items = await restService.RefreshDataAsync();

                foreach (var item in items)
                {
                    Items.Add(item);
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine(ex);
            }
            finally
            {
                Refresh = false;
            }
        }

    }
}
