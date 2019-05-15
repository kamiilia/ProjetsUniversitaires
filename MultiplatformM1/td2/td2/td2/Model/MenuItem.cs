using System;
using System.Collections.Generic;
using System.Text;

namespace td2.Model
{
        public enum Menu
        {
            PlaceItem,
            Profil,
            Logout
        }

        public class MenuItem
        {
            public Menu Id { get; set; }
            public string Title { get; set; }
        }


    }
