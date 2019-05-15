using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Text;
using td2.data;
using Xamarin.Forms;

namespace td2.viewModel
{
    public class BaseViewModel : INotifyPropertyChanged
    {
        public IRestService restService = DependencyService.Get<IRestService>() ?? new RestService();


        // pr mettre a jour la vue
        #region INotifyPropertyChanged
        public event PropertyChangedEventHandler PropertyChanged;
        protected void OnPropertyChanged([CallerMemberName] string propertyName = "")
        {
            var changed = PropertyChanged;
            if (changed == null)
                return;

            changed.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
        #endregion
        string title = string.Empty;
        public string Title
        {
            get { return title; }
            set { SetProperty(ref title, value); }
        }
        string description = string.Empty;
        public string Description
        {
            get { return description; }
            set { SetProperty(ref description, value); }
        }
        string imageurl = string.Empty;
        public string ImageUrl
        {
            get { return imageurl; }
            set { SetProperty(ref imageurl, value); }
        }


        protected bool SetProperty<T>(ref T backingStore, T value,
            [CallerMemberName]string propertyName = "",
            Action onChanged = null)
        {
            if (EqualityComparer<T>.Default.Equals(backingStore, value))
                return false;

            backingStore = value;
            onChanged?.Invoke();
            OnPropertyChanged(propertyName);
            return true;
        }

        private bool refresh = false;
        public bool Refresh
        {
            get { return refresh; }
            set { refresh = value; OnPropertyChanged(); }
        }

    }
}
